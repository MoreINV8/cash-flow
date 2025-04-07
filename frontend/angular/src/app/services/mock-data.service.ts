import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MockDataService {
  mockMonth = () => {
    return ['April 2025', 'March 2025', 'Febuary 2025'];
  }
}
