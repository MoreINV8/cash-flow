import { Category } from './category.type';
import { Day } from './day.type';
import { Month } from './month.type';

export type Note = {
  latest_2_months: {
    month: Month;
    days: Day[];
  }[];
  categories: Category[];
};
