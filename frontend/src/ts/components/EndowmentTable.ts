import {html, LitElement} from "lit";
import {customElement} from "lit/decorators.js";
import {Task} from "@lit/task";
import {type Endowment, EndowmentsApi} from "../api/gen";
import {LocalAPIConfiguration} from "../api/APIConfiguration.ts";

@customElement("endowment-table")
export class EndowmentTable extends LitElement {
  private _endowmentClient = new EndowmentsApi(LocalAPIConfiguration);

  private _endowmentTask = new Task(this, {
    task: async ([], {signal}) => {
      const response = await this._endowmentClient.getEndowments({signal});
      return response;
    },
    args: () => []
  });

  protected render() {
    return html`
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
        </tr>
        </thead>

        <tbody>

        ${this._endowmentTask.render({
          pending: () => '',
          error: (_) => '',
          complete: (endowments: Endowment[]) => html`
            ${endowments.map((endowment) => html`
              <tr>
                <td>${endowment.id}</td>
                <td dir="rtl" lang="ar">${endowment.name}</td>
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
