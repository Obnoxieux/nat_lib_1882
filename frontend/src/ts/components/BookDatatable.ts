import {customElement, property, state} from "lit/decorators.js";
import {html, LitElement} from "lit";
import {Task} from "@lit/task";
import type {Genre} from "../types/Genre.ts";
import type {Endowment} from "../types/Endowment.ts";
import {live} from "lit/directives/live.js";
import {
  AuthorsApi,
  BooksApi,
  EndowmentsApi,
  GenresApi,
  type GetAuthors200Response,
  type GetBooks200Response,
  type GetBooksRequest
} from "../api/gen";
import {LocalAPIConfiguration} from "../api/APIConfiguration.ts";

@customElement("book-datatable")
export class BookDatatable extends LitElement {
  @property({type: Boolean})
  manuscript = true;

  @property({type: Boolean})
  print = false;

  @property({type: Number})
  author: number | null | undefined = undefined;

  @property({type: Number})
  genre = 0;

  @property({type: Number})
  endowment = 0;

  @property({type: Number})
  itemsPerPage = 10;

  @property({type: String})
  search = "";

  @state()
  showFilter = true;

  private _bookClient = new BooksApi(LocalAPIConfiguration);
  private _genreClient = new GenresApi(LocalAPIConfiguration);
  private _endowmentClient = new EndowmentsApi(LocalAPIConfiguration);
  private _authorClient = new AuthorsApi(LocalAPIConfiguration);

  private _bookTask = new Task(this, {
    task: async (
        [
          author, manuscript, print, genre, endowment, itemsPerPage, search
        ], {signal}) => {
      const requestConfig: GetBooksRequest = {
        author: author !== 0 ? author : undefined,
        manuscript: manuscript,
        print: print,
        genre: genre !== 0 ? genre : undefined,
        endowment: endowment !== 0 ? endowment : undefined,
        limit: itemsPerPage,
        search: search !== "" ? search : undefined,
      };
      const response = await this._bookClient.getBooks(requestConfig, {signal});
      return response;
    },
    args: () => [
      this.author, this.manuscript, this.print, this.genre, this.endowment, this.itemsPerPage, this.search,
    ],
  });

  private _authorTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._authorClient.getAuthors({}, {signal});
      return response;
    },
    args: () => []
  });

  private _genreTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._genreClient.getGenres({}, {signal});
      return response;
    },
    args: () => []
  });

  private _endowmentTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._endowmentClient.getEndowments({}, {signal});
      return response;
    },
    args: () => []
  });

  render() {
    return html`

      <div class="form-header">
        <h3>Filter/Search</h3>
        <button @click=${() => this.showFilter = !this.showFilter}>
          ${this.showFilter ? 'Hide' : 'Show'}
        </button>
      </div>

      ${this.getForm()}

      <h3>Results</h3>

      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>No.</th>
          <th>Title</th>
          <th>Author</th>
          <th>Genre</th>
          <th>Endowment</th>
          <th>Manuscript</th>
          <th>Print</th>
          <th>Vol.</th>
          <th>Comment</th>
          <th>Editor Comment</th>
        </tr>
        </thead>

        <tbody>
        ${this._bookTask.render({
          pending: () => html`
            <tr>
              <td colspan="11">
                <progress></progress>
              </td>
            </tr>
          `,
          error: (e) => html`
            <tr>
              <td colspan="11">Failed to load books: ${String(e)}</td>
            </tr>
          `,
          complete: (response: GetBooks200Response) => html`
            ${response.items?.map((book) => html`
                      <tr>
                        <td>${book.id ?? ''}</td>
                        <td>${book.number ?? ''}</td>
                        <td dir="rtl" lang="ar">${book.title ?? ''}</td>
                        <td dir="rtl" lang="ar">
                          ${book.authors?.map(a => html`<span>${a.name}</span>`)}
                        </td>
                        <td dir="rtl" lang="ar">${book.genre?.name ?? ''}</td>
                        <td dir="rtl" lang="ar">${book.endowment?.name ?? ''}</td>
                        <td class="text-center">${book.manuscript ? '✅' : '❌'}</td>
                        <td class="text-center">${book.print ? '✅' : '❌'}</td>
                        <td>${book.volume ?? ''}</td>
                        <td dir="rtl" lang="ar">${book.comment ?? ''}</td>
                        <td>${book.editorComment ?? ''}</td>
                      </tr>
                    `
            )}
          `
        })}

        </tbody>
      </table>
    `;
  }

  protected getForm() {
    if (this.showFilter) {
      return html`
        <form class="book-form">
          <label>
            Author
            <input
                    type="text"
                    placeholder="Author name"
                    list="authors-suggestion-list"
                    dir="rtl"
                    lang="ar"
                    .value=${live(this.author ?? "")}
                    @change=${this.handleAuthorChange}
            >
            <span class="legend">Start typing (in Arabic script) to get suggestions</span>
          </label>

          <datalist id="authors-suggestion-list">
            ${this._authorTask.render({
              pending: () => '',
              error: (_) => '',
              complete: (response: GetAuthors200Response) => html`
                ${response.items?.map((author) => html`
                  <option value="${author.id}" label=${author.name}></option>
                `)}
              `
            })}

          </datalist>

          <label>
            Genre
            <select
                    id="select"
                    dir="rtl"
                    .value=${live(this.genre)}
                    @change=${this.handleGenreChange}>
              >
              <option value="0">All Genres</option>
              ${this._genreTask.render({
                pending: () => '',
                error: (_) => '',
                complete: (genres: Genre[]) => html`
                  ${genres.map((genre) => html`
                    <option value="${genre.id}">${genre.name}</option>
                  `)}
                `
              })}

            </select>
          </label>

          <label>
            Endowment
            <select
                    id="select"
                    dir="rtl"
                    .value=${live(this.endowment)}
                    @change=${this.handleEndowmentChange}>

              >
              <option value="0">All Endowments</option>

              ${this._endowmentTask.render({
                pending: () => '',
                error: (_) => '',
                complete: (endowments: Endowment[]) => html`
                  ${endowments.map((endowment) => html`
                    <option value="${endowment.id}">${endowment.name}</option>
                  `)}
                `
              })}
            </select>
          </label>

          <div class="checkbox-container">
            <label>
              Manuscript
              <input type="checkbox" ?checked=${this.manuscript} @change=${this.handleManuscriptChange}>
            </label>
            <label>
              Print
              <input type="checkbox" ?checked=${this.print} @change=${this.handlePrintChange}>
            </label>
          </div>

          <div class="pagination-container">
            <label>
              Items per page:
              <select .value=${this.itemsPerPage} @change=${this.handleItemsPerPageChange}>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
              </select>
            </label>
          </div>

          <label for="book-search-input">
            Search:
            <input
                    id="book-search-input"
                    placeholder="Search..."
                    type="search"
                    .value=${live(this.search)}
                    @change=${(e: Event) => this.search = (e.target as HTMLInputElement).value}
            >
          </label>
        </form>
      `;
    } else {
      return html``;
    }
  }

  protected handleAuthorChange(e: Event) {
    const value = (e.target as HTMLInputElement).value.trim();
    this.author = value === "" ? undefined : Number(value);
  }

  protected handleGenreChange(e: Event) {
    this.genre = Number((e.target as HTMLSelectElement).value) || 0;
  }

  protected handleEndowmentChange(e: Event) {
    this.endowment = Number((e.target as HTMLSelectElement).value) || 0;
  }

  protected handleManuscriptChange(e: Event) {
    this.manuscript = (e.target as HTMLInputElement).checked;
  }

  protected handlePrintChange(e: Event) {
    this.print = (e.target as HTMLInputElement).checked;
  }

  protected handleItemsPerPageChange(e: Event) {
    this.itemsPerPage = Number((e.target as HTMLSelectElement).value);
  }


  protected createRenderRoot() {
    return this;
  }
}
