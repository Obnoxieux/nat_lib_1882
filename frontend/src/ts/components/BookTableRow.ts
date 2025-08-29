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
    const book = this.book;
    const authors = book.authors ?? [];

    return html`
      <tr>
        <td>${book.id ?? ''}</td>
        <td>${book.number ?? ''}</td>
        <td dir="rtl" lang="ar">${book.title ?? ''}</td>
        <td dir="rtl" lang="ar">
          ${authors.map(a => html`<span>${a.name}</span>`)}
        </td>
        <td dir="rtl" lang="ar">${book.genre?.name ?? ''}</td>
        <td dir="rtl" lang="ar">${book.endowment?.name ?? ''}</td>
        <td class="text-center">${book.manuscript ? '✅' : '❌'}</td>
        <td class="text-center">${book.print ? '✅' : '❌'}</td>
        <td>${book.volume ?? ''}</td>
        <td dir="rtl" lang="ar">${book.comment ?? ''}</td>
        <td>${book.editorComment ?? ''}</td>
      </tr>
    `;
  }

  protected createRenderRoot() {
    return this;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'book-table-row': BookTableRow;
  }
}
