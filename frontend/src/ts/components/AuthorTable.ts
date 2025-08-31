import {customElement} from "lit/decorators.js";
import {AuthorsApi, type GetAuthors200Response} from "../api/gen";
import {LocalAPIConfiguration} from "../api/APIConfiguration.ts";
import {Task} from "@lit/task";
import {html, LitElement} from "lit";

@customElement("author-table")
export class AuthorTable extends LitElement {
  private _authorClient = new AuthorsApi(LocalAPIConfiguration);

  private _authorTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._authorClient.getAuthors({}, {signal});
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

        ${this._authorTask.render({
          pending: () => '',
          error: (_) => '',
          complete: (response: GetAuthors200Response) => html`
            ${response.items?.map((author) => html`
              <tr>
                <td>${author.id}</td>
                <td dir="rtl" lang="ar">${author.name}</td>
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