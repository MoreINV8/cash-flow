import { Component, input } from '@angular/core';
import { ColorSvgDirective } from '../../directives/color-svg.directive';
import { Day } from '../../models/day.type';

@Component({
  selector: 'app-note-table',
  imports: [ColorSvgDirective],
  templateUrl: './note-table.component.html',
  styleUrl: './note-table.component.scss'
})
export class NoteTableComponent {
  days = input.required<Day[]>();
  handleUpdate = input.required<(day: Day) => void>();
  handleDelete = input.required<(day: Day) => void>();
}
