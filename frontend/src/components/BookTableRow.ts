import {css, html, LitElement} from 'lit';
import {customElement, property} from 'lit/decorators.js';
import type {Book} from "../types/Book.ts";

@customElement('book-table-row')
export class BookTableRow extends LitElement {
  static styles = css`
      :host {
          display: contents; /* ensures <tr> participates directly in the table */
      }

      .text-center {
          text-align: center;
      }
  `;

  @property({type: Object})
  book!: Book;

  render() {
    const b = this.book ?? ({} as Book);
    const authors = b.authors ?? [];

    return html`
      <tr>
        <td>${b.id ?? ''}</td>
        <td>${b.number ?? ''}</td>
        <td dir="rtl" lang="ar">${b.title ?? ''}</td>
        <td dir="rtl" lang="ar">
          ${authors.map(a => html`<span>${a.name}</span>`)}
        </td>
        <td dir="rtl" lang="ar">${b.genre?.name ?? ''}</td>
        <td dir="rtl" lang="ar">${b.endowment?.name ?? ''}</td>
        <td class="text-center">${b.manuscript ? '✅' : '❌'}</td>
        <td class="text-center">${b.print ? '✅' : '❌'}</td>
        <td>${b.volume ?? ''}</td>
        <td dir="rtl" lang="ar">${b.comment ?? ''}</td>
        <td>${b.editorComment ?? ''}</td>
      </tr>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'book-table-row': BookTableRow;
  }
}
