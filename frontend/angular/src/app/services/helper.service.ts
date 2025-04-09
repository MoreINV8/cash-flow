import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  private HEX = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];

  public colorGenerate(amount: number = 1, { saturation, lightness }: { saturation: number, lightness: number } = { saturation: 100, lightness: 70 } ) {
    const hslColor: string[] = [];

    for (let i = 1; i <= amount; i++) {
      hslColor.push(
        `hsl(${
          Math.round((i / amount) * 360)
        }, ${saturation}%, ${lightness}%)`
      );
    }

    return hslColor;
  }

  public changeHexOpacity(hexCode: string, opacity: number) {
    if (opacity > 100 || opacity < 0) return;

    // calculate in 1 byte(8 bits)
    const convertedOpacity = Math.round(255 * (opacity / 100));

    return hexCode + this.convertDecimalToHexadecimal(convertedOpacity);
  }

  private convertDecimalToHexadecimal(num: number) {
    let hexadecimal = '';
    while (num > 0) {
      hexadecimal = this.HEX.at(num % 16) + hexadecimal;
      num = Math.floor(num / 16)
    }

    return hexadecimal;
  }

}
