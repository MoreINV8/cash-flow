import { DashboardCategory } from "./dashboard-category.type";
import { DashboardSpend } from "./dashboard-spend.type";
import { DashboardSummary } from "./dashboard-summary.type";
import { DashboardTopRecord } from "./dashboard-top-record.type";

export type Dashboard = {
    month: Date;
    summary: DashboardSummary;
    category_brakedown: DashboardCategory[];
    daily_spending: DashboardSpend[];
    top_spend_records: DashboardTopRecord[];
}