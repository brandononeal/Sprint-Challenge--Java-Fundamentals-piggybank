package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{
    @Autowired
    private CoinRepository coinrepos;

    // http://localhost:2019/total
    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> listCoinTotal()
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(myList::add);

        double totalValue = 0;

        for (Coin c : myList)
        {
            totalValue += c.getValue() * c.getQuantity();

            if (c.getQuantity() == 1)
            {
                System.out.println(c.getQuantity() + " " + c.getName());
            }
            else
            {
                System.out.println(c.getQuantity() + " " + c.getNameplural());
            }
        }

        System.out.println("The piggy bank holds " + totalValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/money/{amount}
    @GetMapping(value = "/money/{amount}", produces = {"application/json"})
    public ResponseEntity<?> removeCoins(@PathVariable double amount)
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(myList::add);

        double totalValue = 7.3;
        double rtnAmount = amount;

        for (Coin c : myList)
        {
            double coinValue = c.getValue() * c.getQuantity();

            if (totalValue - amount < 0)
            {
                System.out.println("Money not available");
                break;
            }
            else if (coinValue - rtnAmount < 0)
            {
                rtnAmount -= coinValue;
            }
            else
            {
                coinValue -= rtnAmount;
                rtnAmount = 0;

                if (c.getQuantity() == 1)
                {
                    System.out.println("$" + Math.round(coinValue * 100.0) / 100.0 + " " + c.getName());
                }
                else
                {
                    System.out.println("$" + Math.round(coinValue * 100.0) / 100.0 + " " + c.getNameplural());
                }
            }
        }

        if (totalValue - amount > 0)
        {
            System.out.println("The piggy bank holds $" + (totalValue - amount));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
