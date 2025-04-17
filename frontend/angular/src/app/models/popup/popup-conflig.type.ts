import { AbstractControl, ValidationErrors } from "@angular/forms";
import { PopupButton } from "./button.type";
import { PopupInput } from "./input.type";

export type PopupConfiguration = {
    title: string;
    ableBgClose: boolean;
    handleClose: () => void;
    handleSubmit: ((control:AbstractControl) => ValidationErrors | null) | undefined;
    description: string | undefined;
    inputField: PopupInput[] | undefined;
    button: PopupButton | undefined;
}