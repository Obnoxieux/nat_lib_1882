import {Configuration} from "./gen";

export const LocalAPIConfiguration = new Configuration({
  basePath: import.meta.env.VITE_BACKEND_URL,
});