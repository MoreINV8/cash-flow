import { Directive, effect, ElementRef, inject, input } from '@angular/core';

@Directive({
  selector: '[appChangeBgColor]',
})
export class ChangeBgColorDirective {
  color = input.required<string>();
  when = input(true);

  private element = inject(ElementRef);

  changeColor = effect(() => {
    if (this.when()) {
      const HTMLElement = this.element.nativeElement as HTMLElement;

      HTMLElement.style.backgroundColor = this.color();
    }
  });
}
