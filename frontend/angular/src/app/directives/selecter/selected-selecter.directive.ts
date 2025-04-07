import { Directive, effect, ElementRef, inject, input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appSelectedSelecter]'
})
export class SelectedSelecterDirective {
  isSelected = input(false);
  private element = inject(ElementRef);
  private renderer = inject(Renderer2);

  changeStyle = effect(() => {
    if (this.isSelected()) {
      this.renderer.addClass(this.element.nativeElement, 'selected-item');
    } else {
      this.renderer.removeClass(this.element.nativeElement, 'selected-item');
    }
  })

}
