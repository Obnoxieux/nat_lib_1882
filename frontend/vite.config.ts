import {dirname, resolve} from "node:path";
import {fileURLToPath} from "node:url";
import {defineConfig} from "vite";
//@ts-ignore
import handlebars from "vite-plugin-handlebars";

const __dirname = dirname(fileURLToPath(import.meta.url));

export default defineConfig({
  plugins: [
    handlebars({
      partialDirectory: resolve(__dirname, "partials"),
    }),
  ],
  build: {
    rollupOptions: {
      input: {
        main: resolve(__dirname, "index.html"),
        openapi: resolve(__dirname, "openapi/index.html"),
        impressum: resolve(__dirname, "impressum/index.html"),
        privacy: resolve(__dirname, "privacy/index.html"),
        books: resolve(__dirname, "data/books.html"),
        authors: resolve(__dirname, "data/authors.html"),
        genres: resolve(__dirname, "data/genres.html"),
        endowments: resolve(__dirname, "data/endowments.html"),
      },
    },
  },
});
