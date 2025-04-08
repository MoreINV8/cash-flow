import { TestBed } from '@angular/core/testing';

import { SvgReaderService } from './svg-reader.service';

describe('SvgReaderService', () => {
  let service: SvgReaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SvgReaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
