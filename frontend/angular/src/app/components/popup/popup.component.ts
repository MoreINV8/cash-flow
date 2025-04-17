import { Component, input, OnChanges, OnInit, signal, SimpleChanges } from '@angular/core';
import { PopupConfiguration } from '../../models/popup/popup-conflig.type';
import { CommonModule } from '@angular/common';
import { ChangeScaleDirective } from '../../directives/change-scale.directive';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidatorFn,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-popup',
  imports: [CommonModule, ChangeScaleDirective, ReactiveFormsModule],
  templateUrl: './popup.component.html',
  styleUrl: './popup.component.scss',
})
export class PopupComponent implements OnChanges {
  isOpen = input(false);
  config = input.required<PopupConfiguration>();
  form = signal<FormGroup>(new FormGroup(''));
  
  ngOnChanges(changes: SimpleChanges): void {
    const newConfig = changes['config'];

    if (newConfig) {
      const formControl: { [key: string]: FormControl } = {};
  
      if (this.config().inputField) {
        this.config().inputField?.forEach((value) => {
          const validators: ValidatorFn[] = [];
  
          if (value.require) validators.push(Validators.required);
          if (value.validator) validators.push(value.validator);
  
          formControl[
            `${this.config().title.replace(' ', '')}-${value.title.replace(
              ' ',
              ''
            )}`
          ] = new FormControl(value.default ?? '', validators);
        });
      }
  
      this.form.set(new FormGroup(formControl));
    }
  }

  closeByBg = (event: MouseEvent) => {
    const element = event.target as HTMLElement;

    if (
      (this.config().ableBgClose ?? true) &&
      element.classList.contains('popup-bg')
    ) {
      this.closePopup()
    }
  };

  closePopup = () => {
    this.config().handleClose();
    this.clearForm();
  }

  onSubmit = () => {
    if (this.form().valid) {
      // Pass the form values to a function or service
      if (this.config().handleSubmit)
        this.config().handleSubmit!(this.form()!.value);

      this.closePopup();
    } else {
      console.log('Form is invalid');
    }
  };

  clearForm = () => {
    this.form().reset();
  }
}
