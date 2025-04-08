import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SvgReaderService {
  public loadSVG = async (filePath: string) => {
    const response = await fetch(filePath);
    const svgContent = await response.text();

    // Parse the SVG string into an HTML element
    const parser = new DOMParser();
    const svgDocument = parser.parseFromString(svgContent, 'image/svg+xml');
    const svgElement = svgDocument.documentElement; // This is the <svg> element

    return svgElement as HTMLElement;
  }
}
