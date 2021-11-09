import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InvoiceBillingDetailComponent } from './invoice-billing-detail.component';

describe('InvoiceBilling Management Detail Component', () => {
  let comp: InvoiceBillingDetailComponent;
  let fixture: ComponentFixture<InvoiceBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvoiceBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ invoice: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(InvoiceBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InvoiceBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load invoice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.invoice).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
