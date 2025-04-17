export type Day = {
  d_id: string;
  date: Date;
  detail: string;
  transaction_value: number;
  note: string;
  category_name: string | undefined;
  category_color: string | undefined;
};