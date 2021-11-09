import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerBillingDetailComponent } from './customer-billing-detail.component';

describe('CustomerBilling Management Detail Component', () => {
  let comp: CustomerBillingDetailComponent;
  let fixture: ComponentFixture<CustomerBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ customer: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(CustomerBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CustomerBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load customer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.customer).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
