import type {Endowment} from "./Endowment.ts";
import type {Genre} from "./Genre.ts";
import type {Author} from "./Author.ts";

export interface Book {
  id: number | string;
  number?: number | string;
  title?: string;
  authors?: Author[];
  genre?: Genre;
  endowment?: Endowment | null;
  manuscript?: boolean;
  print?: boolean;
  volume?: number | string;
  comment?: string;
  editorComment?: string;
}