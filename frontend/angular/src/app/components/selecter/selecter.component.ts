import { Component, input, OnInit, signal } from '@angular/core';
import { DisplaySelecterItemDirective } from '../../directives/selecter/display-selecter-item.directive';
import { DisplayBlockSelecterDirective } from '../../directives/selecter/display-block-selecter.directive';
import { SelecterItemComponent } from '../selecter-item/selecter-item.component';

@Component({
  selector: 'app-selecter',
  imports: [
    DisplaySelecterItemDirective,
    DisplayBlockSelecterDirective,
    SelecterItemComponent,
  ],
  templateUrl: './selecter.component.html',
  styleUrl: './selecter.component.scss',
})
export class SelecterComponent implements OnInit {
  labelList = input.required<string[]>();
  showItems = signal(false);
  label = signal('label');
  selected = signal(0);

  ngOnInit(): void {
    this.label.set(this.labelList()[0]);
  }

  handleDisplaySelecterToggle = () => {
    this.showItems.update((value) => !value);
  };

  handleOnClickItem = (event: MouseEvent, index: number) => {
    const item = event.target as HTMLElement;
    this.selected.set(index);
    this.label.set(item.innerHTML);
    this.showItems.set(false);
  };
}
