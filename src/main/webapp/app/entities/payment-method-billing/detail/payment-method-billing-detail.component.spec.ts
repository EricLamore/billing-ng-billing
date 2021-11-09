import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentMethodBillingDetailComponent } from './payment-method-billing-detail.component';

describe('PaymentMethodBilling Management Detail Component', () => {
  let comp: PaymentMethodBillingDetailComponent;
  let fixture: ComponentFixture<PaymentMethodBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentMethodBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentMethod: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(PaymentMethodBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentMethodBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentMethod on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentMethod).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
