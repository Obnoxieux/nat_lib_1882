import {customElement, property, state} from "lit/decorators.js";
import {html, LitElement} from "lit";
// @ts-ignore
import {BookTableRow} from "./BookTableRow.ts";
import type {Book} from "../types/Book.ts";
import {Task} from "@lit/task";
import type {Genre} from "../types/Genre.ts";
import type {Endowment} from "../types/Endowment.ts";
import {live} from "lit/directives/live.js";

@customElement("book-datatable")
export class BookDatatable extends LitElement {
  @property({type: Boolean})
  manuscript = true;

  @property({type: Boolean})
  print = true;

  @property({type: Number})
  author = 0;

  @property({type: Number})
  genre = 0;

  @property({type: Number})
  endowment = 0;

  @property({type: Number})
  itemsPerPage = 10;

  @state()
  showFilter = true;

  private _bookTask = new Task(this, {
    task: async ([author], {signal}) => {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/books?author=${author}`, {signal});
      if (!response.ok) {
        throw new Error(String(response.status));
      }

      return await response.json() as Book[];
    },
    args: () => [this.author]
  });

  private _genreTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/genres`, {signal});
      if (!response.ok) {
        throw new Error(String(response.status));
      }

      return await response.json() as Genre[];
    },
    args: () => []
  });

  private _endowmentTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/endowments`, {signal});
      if (!response.ok) {
        throw new Error(String(response.status));
      }

      return await response.json() as Endowment[];
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
          complete: (books: Book[]) => html`
            ${books.map((book) => html`
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
            <input type="text" placeholder="Author name..." .value=${live(this.author)}
                   @change=${this.handleAuthorChange}>
          </label>

          <label>
            Genre
            <select id="select" dir="rtl">
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
            <select id="select" dir="rtl">
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
              <input type="checkbox" ?checked=${this.manuscript}>
            </label>
            <label>
              Print
              <input type="checkbox" ?checked=${this.print}>
            </label>
          </div>

          <div class="pagination-container">
            <label>
              Items per page:
              <select .value=${this.itemsPerPage}>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
              </select>
            </label>
          </div>

          <label for="book-search-input">
            Search:
            <input id="book-search-input" placeholder="Search..." type="search">
          </label>
        </form>
      `;
    } else {
      return html``;
    }
  }

  protected handleAuthorChange(e: InputEvent) {
    // @ts-expect-error
    this.author = e.target?.value;
  }

  protected createRenderRoot() {
    return this;
  }
}