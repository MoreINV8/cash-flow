import { Dashboard } from './dashboard/dashboard.type';
import { Month } from './month.type';

export type DashboardWithMonth = {
  noted_month: Month;
  month_dashboard: Dashboard;
};
