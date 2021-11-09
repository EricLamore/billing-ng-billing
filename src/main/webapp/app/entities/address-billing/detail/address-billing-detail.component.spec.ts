import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AddressBillingDetailComponent } from './address-billing-detail.component';

describe('AddressBilling Management Detail Component', () => {
  let comp: AddressBillingDetailComponent;
  let fixture: ComponentFixture<AddressBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddressBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ address: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(AddressBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddressBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load address on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.address).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
