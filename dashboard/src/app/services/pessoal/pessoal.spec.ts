import { TestBed } from '@angular/core/testing';

import { PessoalService } from './pessoal';

describe('Pessoal', () => {
  let service: PessoalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PessoalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
