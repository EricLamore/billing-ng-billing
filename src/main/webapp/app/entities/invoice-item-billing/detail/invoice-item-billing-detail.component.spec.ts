import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InvoiceItemBillingDetailComponent } from './invoice-item-billing-detail.component';

describe('InvoiceItemBilling Management Detail Component', () => {
  let comp: InvoiceItemBillingDetailComponent;
  let fixture: ComponentFixture<InvoiceItemBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvoiceItemBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ invoiceItem: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(InvoiceItemBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InvoiceItemBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load invoiceItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.invoiceItem).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
