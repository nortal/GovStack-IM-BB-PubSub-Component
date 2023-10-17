import { toPagingOptions } from "@/util/helpers";

export function defaultItemsPerPageOptions(
  ...additional: number[]
): { title: string; value: number }[] {
  return toPagingOptions(2, 10, 25, ...additional);
}
