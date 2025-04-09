import { Component, input } from '@angular/core';
import { DashboardTopRecord } from '../../models/dashboard/dashboard-top-record.type';

@Component({
  selector: 'app-top-spend',
  imports: [],
  templateUrl: './top-spend.component.html',
  styleUrl: './top-spend.component.scss'
})
export class TopSpendComponent {
  tableData = input.required<DashboardTopRecord[]>();
}
