import { LitElement, html } from 'lit'
import { customElement } from 'lit/decorators.js'
//@ts-ignore
import { AppNav } from '../nav/app-nav'

@customElement('app-layout')
export class AppLayout extends LitElement {

  render() {
    return html`
      <header>
        <app-nav></app-nav>
      </header>

      <main>
        <slot></slot>
      </main>

      <footer>
        <p>Made with 💖 by dbt</p>
      </footer>
    `
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'app-layout': AppLayout
  }
}
