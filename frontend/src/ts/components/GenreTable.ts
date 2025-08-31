import {customElement} from "lit/decorators.js";
import {type Genre, GenresApi} from "../api/gen";
import {LocalAPIConfiguration} from "../api/APIConfiguration.ts";
import {Task} from "@lit/task";
import {html, LitElement} from "lit";

@customElement("genre-table")
export class GenreTable extends LitElement {
  private _genreClient = new GenresApi(LocalAPIConfiguration);

  private _genreTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._genreClient.getGenres({}, {signal});
      return response;
    },
    args: () => []
  });

  render() {
    return html`
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
        </tr>
        </thead>

        <tbody>

        ${this._genreTask.render({
          pending: () => '',
          error: (_) => '',
          complete: (genres: Genre[]) => html`
            ${genres.map((genre) => html`
              <tr>
                <td>${genre.id}</td>
                <td dir="rtl" lang="ar">${genre.name}</td>
              </tr>
            `)}
          `
        })}

        </tbody>
      </table>
    `;
  }

  protected createRenderRoot() {
    return this;
  }
}