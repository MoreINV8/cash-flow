import { Directive, effect, ElementRef, inject, input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appChangeTextColor]'
})
export class ChangeTextColorDirective {
  color = input.required<string>();
  when = input(true);
  
  private element = inject(ElementRef);

  changeColor = effect(() => {
    if (this.when()) {
      const HTMLElement = this.element.nativeElement as HTMLElement;

      HTMLElement.style.color = this.color();
    }
  });
}
