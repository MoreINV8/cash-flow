import { Component, input } from '@angular/core';
import { PopupConfiguration } from '../../models/popup/popup-conflig.type';
import { CommonModule } from '@angular/common';
import { ChangeScaleDirective } from '../../directives/change-scale.directive';

@Component({
  selector: 'app-popup',
  imports: [CommonModule, ChangeScaleDirective],
  templateUrl: './popup.component.html',
  styleUrl: './popup.component.scss'
})
export class PopupComponent {
  isOpen = input(false);
  config = input.required<PopupConfiguration>();

  closeByBg = (event: MouseEvent) => {
    const element = event.target as HTMLElement;
    
    if (
      this.config().ableBgClose ??
      (true && element.classList.contains('popup-bg'))
    ) {
      this.config().handleClose(event);
    }
  }
}
