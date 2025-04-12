import { Directive, effect, ElementRef, inject, input } from '@angular/core';

@Directive({
  selector: '[appChangeScale]'
})
export class ChangeScaleDirective {
  smallScale = input.required<number>();
  largeScale = input.required<number>();
  triggerLarge = input.required<boolean>();

  element = inject(ElementRef);

  changeScale = effect(() => {
    const htmlElement = this.element.nativeElement as HTMLElement;
    if (this.triggerLarge()) {
      htmlElement.style.scale = `${this.largeScale()}`;
    } else {
      htmlElement.style.scale = `${this.smallScale()}`;
    }
  });
  

}
