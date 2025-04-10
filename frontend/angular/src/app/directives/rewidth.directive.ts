import { Directive, effect, ElementRef, inject, input } from '@angular/core';

@Directive({
  selector: '[appRewidth]'
})
export class RewidthDirective {
  shortMaxLength = input.required<string>();
  longMaxLength = input.required<string>();
  triggerShortToLong = input.required<boolean>();

  private element = inject(ElementRef);
  
  rewith = effect(() => {
    const html = this.element.nativeElement as HTMLElement;
    if (this.triggerShortToLong()) {
      html.style.maxWidth = this.longMaxLength();
    } else {
      html.style.maxWidth = this.shortMaxLength();
    }
  })

}
