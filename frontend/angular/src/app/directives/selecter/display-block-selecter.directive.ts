import { Directive, effect, ElementRef, inject, input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appDisplayBlockSelecter]'
})
export class DisplayBlockSelecterDirective {
  isDisplay = input(false);
  private element = inject(ElementRef);
  private renderer = inject(Renderer2);

  changeStyle = effect(() => {
    if (this.isDisplay()) {
      this.renderer.addClass(this.element.nativeElement, "sharp-edge");
    } else {
      this.renderer.removeClass(this.element.nativeElement, "sharp-edge");
    }
  })

}
