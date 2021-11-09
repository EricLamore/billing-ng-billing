jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SettingsInvoiceBillingService } from '../service/settings-invoice-billing.service';
import { ISettingsInvoiceBilling, SettingsInvoiceBilling } from '../settings-invoice-billing.model';

import { SettingsInvoiceBillingUpdateComponent } from './settings-invoice-billing-update.component';

describe('SettingsInvoiceBilling Management Update Component', () => {
  let comp: SettingsInvoiceBillingUpdateComponent;
  let fixture: ComponentFixture<SettingsInvoiceBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let settingsInvoiceService: SettingsInvoiceBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SettingsInvoiceBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SettingsInvoiceBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SettingsInvoiceBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    settingsInvoiceService = TestBed.inject(SettingsInvoiceBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const settingsInvoice: ISettingsInvoiceBilling = { id: 'CBA' };

      activatedRoute.data = of({ settingsInvoice });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(settingsInvoice));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceBilling>>();
      const settingsInvoice = { id: 'ABC' };
      jest.spyOn(settingsInvoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsInvoice }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(settingsInvoiceService.update).toHaveBeenCalledWith(settingsInvoice);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceBilling>>();
      const settingsInvoice = new SettingsInvoiceBilling();
      jest.spyOn(settingsInvoiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsInvoice }));
      saveSubject.complete();

      // THEN
      expect(settingsInvoiceService.create).toHaveBeenCalledWith(settingsInvoice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceBilling>>();
      const settingsInvoice = { id: 'ABC' };
      jest.spyOn(settingsInvoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(settingsInvoiceService.update).toHaveBeenCalledWith(settingsInvoice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
