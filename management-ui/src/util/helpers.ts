import { AxiosResponse } from 'axios';

export function toPagingOptions(
  ...options: number[]
): { title: string; value: number }[] {
  return options.map((value) => {
    return { title: value + "", value };
  });
}

// Helper to copy text to clipboard
export function toClipboard(val: string): void {
  // If a dialog is overlaying the entire page we need to put the textbox inside it, otherwise it doesn't get copied
  const container =
    document.getElementsByClassName("v-dialog--active")[0] || document.body;
  const tempValueContainer = document.createElement("input");
  tempValueContainer.setAttribute("type", "text");
  tempValueContainer.style.zIndex = "300";
  tempValueContainer.style.opacity = "0";
  tempValueContainer.style.filter = "alpha(opacity=0)";
  tempValueContainer.setAttribute(
    "data-test",
    "generated-temp-value-container",
  );
  tempValueContainer.value = val;
  container.appendChild(tempValueContainer);
  tempValueContainer.select();
  document.execCommand("copy");
  container.removeChild(tempValueContainer);
}

type DeepReadonlyArray<T> = ReadonlyArray<DeepReadonly<T>>;
type DeepReadonly<T> = {
  readonly [P in keyof T]: T[P] extends (infer R)[] ? DeepReadonlyArray<R> : DeepReadonly<T[P]>;
};

export function formatDateToUTC(date: Date): string {
  const year = date.getUTCFullYear();
  const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
  const day = date.getUTCDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}

// Format date. Result YYYY-MM-DD HH:MM.
export function formatTimestamp(date?: Date): string {
  if (date === undefined || date === null) {
    return "-";
  }
  return (
      date.getFullYear() +
      "-" +
      (date.getMonth() + 1).toString().padStart(2, "0") +
      "-" +
      date.getDate().toString().padStart(2, "0") +
      " " +
      date.getHours().toString().padStart(2, "0") +
      ":" +
      date.getMinutes().toString().padStart(2, "0")
  );
}

export function saveResponseAsFile(
  schema: string,
  defaultFileName = 'schema.json',
): void {
  const blob = new Blob([schema], {
    type: 'application/json',
  });

  // Create a link to DOM and click it. This will trigger the browser to start file download.
  const link = document.createElement('a');
  link.href = window.URL.createObjectURL(blob);
  link.setAttribute('download', defaultFileName);
  link.setAttribute('data-test', 'generated-download-link');
  document.body.appendChild(link);
  link.click();

  // cleanup
  document.body.removeChild(link);
  URL.revokeObjectURL(link.href);
}