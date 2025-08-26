import { LitElement, css, html } from 'lit'
import { customElement } from 'lit/decorators.js'

@customElement('app-nav')
export class AppNav extends LitElement {
  static styles = css`
    nav {
      display: block;
      padding: 0.5rem 1rem;
      background: var(--app-nav-bg, #111827);
    }
    ul {
      list-style: none;
      margin: 0;
      padding: 0;
      display: flex;
      gap: 1rem;
    }
    a {
      color: var(--app-nav-link, #93c5fd);
      text-decoration: none;
      font-weight: 500;
    }
    a:hover,
    a:focus {
      text-decoration: underline;
    }
  `

  render() {
    return html`
      <nav aria-label="Main navigation">
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/third.html">NESTED PAGE</a></li>
          <li><a href="/blog">Blog</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </nav>
    `
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'app-nav': AppNav
  }
}