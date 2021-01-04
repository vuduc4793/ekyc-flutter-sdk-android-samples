import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {


  static const platform = const MethodChannel('com.vnpt.ekyc/sdk');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Home Screen")),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
              alignment: Alignment.center,
              child: DecoratedBox(
                decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(6)),
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 20),
                  child: GestureDetector(
                      onTap: _openEKYC,
                      child: Text("START EKYC", style: TextStyle(color: Colors.white,
                          fontSize: 16, fontWeight: FontWeight.bold))),
                ),
              )
          )
        ],
      ),
    );
  }

  Future<void> _openEKYC() async {
    try {
      // method bên trong code native
       await platform.invokeMethod('startEKYC');
    } on PlatformException catch (e) {
      print(e);
    }
  }


}
