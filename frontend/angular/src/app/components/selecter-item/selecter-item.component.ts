import { Component, input } from '@angular/core';
import { SelectedSelecterDirective } from '../../directives/selecter/selected-selecter.directive';

@Component({
  selector: 'app-selecter-item',
  imports: [SelectedSelecterDirective],
  templateUrl: './selecter-item.component.html',
  styleUrl: './selecter-item.component.scss',
})
export class SelecterItemComponent {
  isSelect = input(false);
  index = input.required<number>();
  label = input.required<string>();
  onclick = input.required<(event: MouseEvent, index: number) => void>();
}
