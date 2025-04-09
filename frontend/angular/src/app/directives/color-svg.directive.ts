import {
  Directive,
  effect,
  ElementRef,
  inject,
  input,
  Renderer2,
} from '@angular/core';
import { SvgReaderService } from '../services/svg-reader.service';

@Directive({
  selector: '[appColorSvg]',
})
export class ColorSvgDirective {
  color = input.required<string>();
  filePath = input.required<string>();
  private svgReader = inject(SvgReaderService);
  private element = inject(ElementRef);
  private renderer = inject(Renderer2);

  addSvgElement = effect(async () => {
    const svgElement = await this.svgReader.loadSVG(this.filePath());

    if (svgElement) {
      svgElement.style.height = '26px';
      svgElement.style.width = '26px';
      svgElement.style.fill = this.color();

      this.renderer.appendChild(this.element.nativeElement, svgElement);
    }
  });
}
