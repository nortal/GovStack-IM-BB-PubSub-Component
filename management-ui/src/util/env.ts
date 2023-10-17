import {ConfigParamName} from "@/global";

export default function getEnv(paramName: ConfigParamName): string {
    const value = window?.configs?.[paramName];

    if (!value) {
        console.warn(`Missing configuration for: ${paramName}`);
        return "";
    }

    return value;
}
