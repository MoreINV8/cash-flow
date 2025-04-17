import { AbstractControl, ValidationErrors } from '@angular/forms';
import { Category } from '../category.type';

export type PopupInput = {
  type:
    | 'button'
    | 'checkbox'
    | 'color'
    | 'date'
    | 'datetime-local'
    | 'email'
    | 'file'
    | 'hidden'
    | 'image'
    | 'month'
    | 'number'
    | 'password'
    | 'radio'
    | 'range'
    | 'reset'
    | 'search'
    | 'submit'
    | 'tel'
    | 'text'
    | 'time'
    | 'url'
    | 'week'
    | 'text'
    | 'password'
    | 'submit'
    | 'reset'
    | 'radio'
    | 'checkbox'
    | 'button'
    | 'color'
    | 'date'
    | 'datetime-local'
    | 'email'
    | 'image'
    | 'file'
    | 'hidden'
    | 'month'
    | 'number'
    | 'range'
    | 'search'
    | 'tel'
    | 'time'
    | 'url'
    | 'week'
    | 'option';
  title: string;
  placeholder: string | undefined;
  require: boolean;
  optionItems: Category[] | undefined;
  default: any | undefined;
  validator:
    | ((control: AbstractControl) => ValidationErrors | null)
    | undefined;
};
