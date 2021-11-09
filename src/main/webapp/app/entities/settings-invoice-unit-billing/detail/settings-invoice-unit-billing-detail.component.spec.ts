import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SettingsInvoiceUnitBillingDetailComponent } from './settings-invoice-unit-billing-detail.component';

describe('SettingsInvoiceUnitBilling Management Detail Component', () => {
  let comp: SettingsInvoiceUnitBillingDetailComponent;
  let fixture: ComponentFixture<SettingsInvoiceUnitBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SettingsInvoiceUnitBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ settingsInvoiceUnit: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SettingsInvoiceUnitBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SettingsInvoiceUnitBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load settingsInvoiceUnit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.settingsInvoiceUnit).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
