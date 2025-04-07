import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () => {
      return import('./pages/dashboard/dashboard.component').then(
        (m) => m.DashboardComponent
      );
    },
  },
];
