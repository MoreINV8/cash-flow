import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DisplaySelecterItemDirective } from '../../directives/selecter/display-selecter-item.directive';
import { DisplayBlockSelecterDirective } from '../../directives/selecter/display-block-selecter.directive';
import { SelecterItemComponent } from '../selecter-item/selecter-item.component';
import { Month } from '../../models/month.type';
import { HelperService } from '../../services/helper.service';

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
  private helper = inject(HelperService);

  monthList = input.required<Month[]>();
  changeItemHandler = input<(mId: string, selected: number) => {}>();
  label = input.required<number>();
  showItems = signal(false);
  labelList = signal<string[]>([]);

  ngOnInit(): void {
    this.monthList().map((value, index) => {
      const l = this.helper.convertMonth(value)

      this.labelList().push(l ?? '');
    })
  }

  handleDisplaySelecterToggle = () => {
    this.showItems.update((value) => !value);
  };

  handleOnClickItem = (event: MouseEvent, index: number) => {
    const item = event.target as HTMLElement;
    this.showItems.set(false);

    if (this.changeItemHandler()) {
      this.changeItemHandler()!(this.monthList().at(index)!.m_id, index);
    } 
  };
}
