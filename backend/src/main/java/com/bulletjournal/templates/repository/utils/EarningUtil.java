package com.bulletjournal.templates.repository.utils;

import com.bulletjournal.templates.controller.model.StockTickerDetails;
import org.apache.commons.lang3.tuple.Pair;

public class EarningUtil extends InvestmentUtil {
    public EarningUtil(String raw) {
        super(raw);
    }

    private static final String EARNINGS_CONTENT_TEMPLATE = "{\"delta\":{\"ops\":" +
            "[{\"attributes\":{\"bold\":true},\"insert\":\"Date\"}," +
            "{\"insert\":\": EARNINGS_DATE\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Time\"}," +
            "{\"insert\":\": EARNINGS_TIME\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Ticker\"}," +
            "{\"insert\":\": EARNINGS_TICKER\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Exchange\"}," +
            "{\"insert\":\": EARNINGS_EXCHANGE\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Name\"}," +
            "{\"insert\":\": EARNINGS_NAME\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Period\"}," +
            "{\"insert\":\": EARNINGS_PERIOD_PERIOD\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Period Year\"}," +
            "{\"insert\":\": EARNINGS_PERIOD_YEAR\\n\"},{\"attributes\":{\"bold\":true},\"insert\":\"Currency\"}," +
            "{\"insert\":\": EARNINGS_CURRENCY\\n\"}EARNINGS_PRIOR_EPS_DELTA EARNINGS_PRIOR_REVENUE_DELTA TICKER_DETAILS_DELTA]},\"###html###\":\"<p>" +
            "<strong>Date</strong>: EARNINGS_DATE</p><p><strong>Time</strong>: EARNINGS_TIME</p>" +
            "<p><strong>Ticker</strong>: EARNINGS_TICKER</p><p><strong>Exchange</strong>: EARNINGS_EXCHANGE</p><p><strong>Name</strong>: EARNINGS_NAME</p><p>" +
            "<strong>Period</strong>: EARNINGS_PERIOD_PERIOD</p><p><strong>Period Year</strong>: EARNINGS_PERIOD_YEAR</p><p><strong>Currency</strong>: EARNINGS_CURRENCY</p>" +
            "EARNINGS_PRIOR_EPS_HTML" +
            "EARNINGS_PRIOR_REVENUE_HTML TICKER_DETAILS_HTML\"}";

    @Override
    public String getContent(StockTickerDetails stockTickerDetails) {
        Pair<String, String> stockTickerDetailContent = getStockTickerDetailContent(stockTickerDetails);
        String priorEpsDelta = "";
        String priorEpsHtml = "";
        if (this.json.get("eps_prior") != null) {
            priorEpsDelta = ",{\"attributes\":{\"bold\":true},\"insert\":\"Prior EPS: \"}," +
                    "{\"insert\": \"" + this.json.get("eps_prior").getAsString() + "\\n\"}";
            priorEpsHtml = "<p><strong>Prior EPS</strong>: " + this.json.get("eps_prior").getAsString() + "</p>";
        }
        String priorRevenueDelta = "";
        String priorRevenueHtml = "";
        if (this.json.get("revenue_prior") != null) {
            priorRevenueDelta = ",{\"attributes\":{\"bold\":true},\"insert\":\"Prior Revenue: \"}," +
                    "{\"insert\": \"" + this.json.get("revenue_prior").getAsString() + "\\n\"}";
            priorRevenueHtml = "<p><strong>Prior Revenue</strong>: " + this.json.get("revenue_prior").getAsString() + "</p>";
        }

        return EARNINGS_CONTENT_TEMPLATE
                .replace("EARNINGS_DATE", this.json.get("date").getAsString())
                .replace("EARNINGS_TIME", this.json.get("time").getAsString())
                .replace("EARNINGS_TICKER", this.json.get("ticker").getAsString())
                .replace("EARNINGS_EXCHANGE", this.json.get("exchange").getAsString())
                .replace("EARNINGS_NAME", this.json.get("name").getAsString())
                .replace("EARNINGS_PERIOD_PERIOD", this.json.get("period").getAsString())
                .replace("EARNINGS_PERIOD_YEAR", this.json.get("period_year").getAsString())
                .replace("EARNINGS_CURRENCY", this.json.get("currency").getAsString())
                .replace("EARNINGS_PRIOR_EPS_HTML", priorEpsHtml)
                .replace("EARNINGS_PRIOR_EPS_DELTA", priorEpsDelta)
                .replace("EARNINGS_PRIOR_REVENUE_HTML", priorRevenueHtml)
                .replace("EARNINGS_PRIOR_REVENUE_DELTA", priorRevenueDelta)
                .replace("TICKER_DETAILS_DELTA", stockTickerDetailContent.getLeft())
                .replace("TICKER_DETAILS_HTML", stockTickerDetailContent.getRight());
    }
}