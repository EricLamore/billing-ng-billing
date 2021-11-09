import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RefundBillingDetailComponent } from './refund-billing-detail.component';

describe('RefundBilling Management Detail Component', () => {
  let comp: RefundBillingDetailComponent;
  let fixture: ComponentFixture<RefundBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RefundBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ refund: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(RefundBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RefundBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load refund on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.refund).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
