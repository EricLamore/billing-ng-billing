import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SettingsInvoiceBillingDetailComponent } from './settings-invoice-billing-detail.component';

describe('SettingsInvoiceBilling Management Detail Component', () => {
  let comp: SettingsInvoiceBillingDetailComponent;
  let fixture: ComponentFixture<SettingsInvoiceBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SettingsInvoiceBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ settingsInvoice: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SettingsInvoiceBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SettingsInvoiceBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load settingsInvoice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.settingsInvoice).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
