import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductRatePlanChargeBillingDetailComponent } from './product-rate-plan-charge-billing-detail.component';

describe('ProductRatePlanChargeBilling Management Detail Component', () => {
  let comp: ProductRatePlanChargeBillingDetailComponent;
  let fixture: ComponentFixture<ProductRatePlanChargeBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductRatePlanChargeBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productRatePlanCharge: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductRatePlanChargeBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductRatePlanChargeBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productRatePlanCharge on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productRatePlanCharge).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
