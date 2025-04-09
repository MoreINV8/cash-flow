import { ChangeDetectorRef, Component, inject, OnInit, signal } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { SelecterComponent } from '../../components/selecter/selecter.component';
import { MockDataService } from '../../services/mock-data.service';
import { BackendConnectService } from '../../services/backend-connect.service';
import { User } from '../../models/user.type';
import { Dashboard } from '../../models/dashboard/dashboard.type';
import { lastValueFrom } from 'rxjs';
import { SummaryComponent } from '../../components/summary/summary.component';
import { DashboardSummaryElement } from '../../models/dashboard/dashboard-summary-element.type';
import { BarLineChartComponent } from '../../components/bar-line-chart/bar-line-chart.component';
import { CommonModule } from '@angular/common';
import { PieChartComponent } from '../../components/pie-chart/pie-chart.component';

@Component({
  selector: 'app-dashboard',
  imports: [
    HeaderComponent,
    SelecterComponent,
    SummaryComponent,
    BarLineChartComponent,
    CommonModule,
    PieChartComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  mockMonth = signal<string[]>([]);
  private backend = inject(BackendConnectService);
  private user: User | null = null;
  data = signal<Dashboard | null>(null);
  summaryDecorate = signal<Map<string, DashboardSummaryElement>>(new Map());
  private changeDetectorRef = inject(ChangeDetectorRef)

  constructor(private mock: MockDataService) {
  }
  
  async ngOnInit(): Promise<void> {
    this.summaryDecorate().set('total-spend', {
      iconPath: '/icons/dollar.svg',
      iconBackground: '#dbeafe',
      iconColor: '#2563eb',
      label: 'Total Spending',
      value: 0,
    });
    this.summaryDecorate().set('average', {
      iconPath: '/icons/chart-histogram.svg',
      iconBackground: '#dcfce7',
      iconColor: '#16a34a',
      label: 'Average Daily Spending',
      value: 0,
    });
    this.summaryDecorate().set('highest', {
      iconPath: '/icons/exclamation.svg',
      iconBackground: '#fef9c3',
      iconColor: '#ca8a04',
      label: 'Highest Expense',
      value: 0,
    });

    this.mockMonth.set(this.mock.mockMonth());
    try {
      const user = await lastValueFrom(
        this.backend.postLogin({ username: 'time', password: 't123' })
      );
      this.user = user;
      console.log(user);
      
      if (this.user == null) {
        console.log('User is null');
        return;
      }
      
      const data = await lastValueFrom(
        this.backend.getChangeDashboard({
          user: this.user,
          monthId: 'ddb6ef9a10f4488fbff8383de03551ba',
        })
      );
      this.data.set(data);
      console.log(data);

      if (this.data() == null) {
        console.log('Data is null');
        return;
      }

      const totalSpendElement = this.summaryDecorate().get('total-spend');
      if (totalSpendElement) {
        totalSpendElement.value = this.data()?.summary.total_spending ?? 0;
      }
      const averageElement = this.summaryDecorate().get('average');
      if (averageElement) {
        averageElement.value = this.data()?.summary.average_daily_spending ?? 0;
      }
      const highestElement = this.summaryDecorate().get('highest');
      if (highestElement) {
        highestElement.value =
          this.data()?.top_spend_records.at(0)?.amount ?? 0;
      }

      console.log(this.summaryDecorate());

      this.changeDetectorRef.detectChanges();

      return;
    } catch (error) {
      console.error('Error:', error);
    }
  }
}
