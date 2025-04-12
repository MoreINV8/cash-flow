import { PopupButton } from "./button.type";
import { PopupInput } from "./input.type";

export type PopupConfiguration = {
    title: string;
    ableBgClose: boolean;
    handleClose: (event: MouseEvent) => void;
    handleSubmit: () => void | undefined;
    description: string | undefined;
    inputField: PopupInput[] | undefined;
    button: PopupButton | undefined;
}